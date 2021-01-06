import matplotlib.pyplot as plt
import pandas as pd


df = pd.read_csv("results.csv")
df.columns = ["x1","x2","categories"]

# ten colors
colors = ['r','g','b','c','m','y','brown','peru','grey','dodgerblue']
plt.scatter(list(df['x1']), list(df['x2']), s = 5, c=list(df['categories'].apply(lambda x: colors[x-1])))

plt.show()


'''
x = range(100)
y = range(100,200)
fig = plt.figure()
ax1 = fig.add_subplot(111)

ax1.scatter(x[:4], y[:4], s=10, c='b', marker="s", label='first')
ax1.scatter(x[40:],y[40:], s=10, c='r', marker="o", label='second')
plt.legend(loc='upper left');
plt.show()
'''