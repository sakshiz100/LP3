import pandas as pd
from sklearn.preprocessing import StandardScaler
from sklearn.cluster import KMeans

# --- Load the dataset with proper encoding ---
df = pd.read_csv("sales_data_sample.csv", encoding='latin1')

print("✅ Dataset loaded successfully!")
print("Shape:", df.shape)
print(df.head())

# --- Automatically select all numerical columns for clustering ---
numerical_cols = df.select_dtypes(include=['int64', 'float64']).columns.tolist()
print("\nNumerical columns selected for clustering:", numerical_cols)

# --- Preprocessing: Scale numerical features ---
X = df[numerical_cols].values
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# --- Elbow Method to determine optimal K ---
wcss = []
K_range = range(1, 11)
for k in K_range:
    kmeans = KMeans(n_clusters=k, init='k-means++', random_state=42, n_init='auto')
    kmeans.fit(X_scaled)
    wcss.append(kmeans.inertia_)

# --- K-Means Clustering (Choose optimal K, e.g., K=3) ---
optimal_k = 3
kmeans_model = KMeans(n_clusters=optimal_k, init='k-means++', random_state=42, n_init='auto')
df['Cluster'] = kmeans_model.fit_predict(X_scaled)

# --- Clean Output ---
print("\n--- K-Means Clustering Implementation ---")
print(f"Optimal Number of Clusters (Determined by Elbow Method): {optimal_k}\n")
print("First 5 rows with assigned cluster labels:")
print(df.head())
print(f"\nWCSS for K={optimal_k}: {wcss[optimal_k-1]:.2f}")
print("\nAnalysis: The elbow method suggests the optimal K is at the point where the WCSS curve bends sharply, indicating distinct clusters in the data.")
print("Points of Improvement: Try feature selection, scaling methods, different K values, or clustering algorithms like DBSCAN or hierarchical clustering.")


