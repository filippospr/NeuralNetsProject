import matplotlib.pyplot as plt
import pandas as pd


df = pd.read_csv("results.csv")
df.columns = ["x1","x2","categories"]

colors = ['r','g','b','c']
plt.scatter(list(df['x1']), list(df['x2']), s = 5, c=list(df['categories'].apply(lambda x: colors[x-1])))

plt.show()