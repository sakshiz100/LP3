import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.neighbors import KNeighborsClassifier
from sklearn.svm import SVC
from sklearn.metrics import confusion_matrix, accuracy_score, precision_score, recall_score, f1_score

# --- Load Dataset ---
df = pd.read_csv("emails.csv")

print("✅ Dataset loaded successfully!")
print("Shape:", df.shape)
print(df.head())

# --- Separate features (X) and labels (y) ---
# All columns except 'Prediction' are features
X = df.drop(columns=['Prediction'])
y = df['Prediction']

# Drop non-numeric or irrelevant columns if any (like 'Email No.')
if 'Email No.' in X.columns:
    X = X.drop(columns=['Email No.'])

# --- Split Dataset ---
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.25, random_state=42, stratify=y
)

print("\n--- Model Training and Performance Analysis ---")

# --- K-Nearest Neighbors (KNN) ---
knn_model = KNeighborsClassifier(n_neighbors=5)
knn_model.fit(X_train, y_train)
knn_pred = knn_model.predict(X_test)

# --- Support Vector Machine (SVM) ---
svm_model = SVC(kernel='linear', random_state=42)
svm_model.fit(X_train, y_train)
svm_pred = svm_model.predict(X_test)


# --- Evaluation Function ---
def evaluate_classifier(y_true, y_pred, model_name):
    acc = accuracy_score(y_true, y_pred)
    prec = precision_score(y_true, y_pred, zero_division=0)
    rec = recall_score(y_true, y_pred, zero_division=0)
    f1 = f1_score(y_true, y_pred, zero_division=0)
    cm = confusion_matrix(y_true, y_pred)

    print(f"\n--- {model_name} Performance ---")
    print(f"Accuracy:  {acc:.4f}")
    print(f"Precision: {prec:.4f}")
    print(f"Recall:    {rec:.4f}")
    print(f"F1-score:  {f1:.4f}")
    print(f"Confusion Matrix:\n{cm}")
    return acc, f1


# --- Evaluate Both Models ---
knn_acc, knn_f1 = evaluate_classifier(y_test, knn_pred, "K-Nearest Neighbors")
svm_acc, svm_f1 = evaluate_classifier(y_test, svm_pred, "Support Vector Machine")

# --- Comparison ---
if svm_acc > knn_acc:
    print("\n✅ Conclusion: Support Vector Machine performs better overall.")
else:
    print("\n✅ Conclusion: K-Nearest Neighbors performs better overall.")

print("\nAnalysis complete — compare Accuracy and F1-score to confirm.")

