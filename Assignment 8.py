import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'  # Suppress TensorFlow INFO/WARNING

import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import MinMaxScaler, LabelEncoder
from sklearn.metrics import accuracy_score, confusion_matrix
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, Input

# --- Load Dataset ---
df = pd.read_csv("Churn_Modelling.csv")

# --- Preprocessing ---
df = df.drop(columns=['RowNumber', 'CustomerId', 'Surname'])
df['Gender'] = LabelEncoder().fit_transform(df['Gender'])
df = pd.get_dummies(df, columns=['Geography'], drop_first=True)

X = df.drop(columns=['Exited']).values
y = df['Exited'].values

X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=42, stratify=y
)

scaler = MinMaxScaler()
X_train = scaler.fit_transform(X_train)
X_test = scaler.transform(X_test)

# --- Neural Network ---
model = Sequential([
    Input(shape=(X_train.shape[1],)),
    Dense(12, activation='relu'),
    Dense(8, activation='relu'),
    Dense(1, activation='sigmoid')
])

model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])

# Train model silently
model.fit(X_train, y_train, epochs=20, batch_size=32, verbose=0, validation_split=0.2)

# Predict
nn_pred = (model.predict(X_test) > 0.5).astype(int)

# Evaluate
acc = accuracy_score(y_test, nn_pred)
cm = confusion_matrix(y_test, nn_pred)

# --- Final Clean Output ---
print("--- Neural Network Performance ---")
print(f"Accuracy Score: {acc:.4f}")
print("Confusion Matrix:")
print(cm)
print("Points of Improvement: Adam, Learning Rate, Layer Size, Early Stopping, Regularization, Hyperparameter Tuning.")


