import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import mean_squared_error, r2_score
from geopy.distance import geodesic

# --- 1. Load the dataset ---
# Dataset link: https://www.kaggle.com/datasets/yasserh/uber-fares-dataset
data = pd.read_csv("uber.csv")

print("Original Dataset Shape:", data.shape)
print(data.head())

# --- 2. Basic cleaning ---
data = data.dropna(subset=["pickup_longitude", "pickup_latitude",
                           "dropoff_longitude", "dropoff_latitude", "fare_amount"])

# Remove invalid or unrealistic values
data = data[(data["fare_amount"] > 0) & (data["fare_amount"] < 500)]
data = data[(data["pickup_longitude"].between(-180, 180)) &
            (data["dropoff_longitude"].between(-180, 180)) &
            (data["pickup_latitude"].between(-90, 90)) &
            (data["dropoff_latitude"].between(-90, 90))]

# --- 3. Feature Engineering ---
# Calculate distance between pickup and dropoff points using geodesic (km)
def calculate_distance(row):
    pickup = (row["pickup_latitude"], row["pickup_longitude"])
    dropoff = (row["dropoff_latitude"], row["dropoff_longitude"])
    return geodesic(pickup, dropoff).km

data["distance_km"] = data.apply(calculate_distance, axis=1)
data = data[data["distance_km"] < 100]  # remove extreme long trips

# --- 4. Prepare data ---
X = data[["distance_km"]]
y = data["fare_amount"]

# Split into train-test
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=42
)

print("\nData Ready for Training:")
print("Training Samples:", len(X_train))
print("Testing Samples:", len(X_test))

# --- 5. Train Linear Regression ---
lr_model = LinearRegression()
lr_model.fit(X_train, y_train)
lr_pred = lr_model.predict(X_test)

# --- 6. Train Random Forest Regressor ---
rf_model = RandomForestRegressor(n_estimators=100, random_state=42)
rf_model.fit(X_train, y_train)
rf_pred = rf_model.predict(X_test)

# --- 7. Evaluate Models ---
print("\n--- Model Evaluation ---")

lr_r2 = r2_score(y_test, lr_pred)
lr_rmse = np.sqrt(mean_squared_error(y_test, lr_pred))
print(f"Linear Regression -> R2: {lr_r2:.4f}, RMSE: {lr_rmse:.4f}")

rf_r2 = r2_score(y_test, rf_pred)
rf_rmse = np.sqrt(mean_squared_error(y_test, rf_pred))
print(f"Random Forest     -> R2: {rf_r2:.4f}, RMSE: {rf_rmse:.4f}")

# --- 8. Compare Models ---
if rf_r2 > lr_r2:
    print("\nConclusion: ✅ Random Forest performs better.")
else:
    print("\nConclusion: ✅ Linear Regression performs better.")

# --- 9. Show sample predictions ---
results = pd.DataFrame({
    "Actual": y_test.values[:10],
    "LR_Predicted": lr_pred[:10],
    "RF_Predicted": rf_pred[:10]
})
print("\nSample Predictions:")
print(results)
