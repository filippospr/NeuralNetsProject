import matplotlib.pyplot as plt
import pandas as pd
import sys

ptype = str(sys.argv[1])


clusters = [2,3,4,5,6,7,10]

df = pd.read_csv("results2.csv",header=None)

df.columns = ["x","y","dummy"]
del df['dummy']

# ten colors
colors = ['r','g','b','c','m','y','brown','peru','grey','dodgerblue']


for i in range(len(clusters)):
    f = open(ptype+"Data/plot"+ptype+str(clusters[i])+".csv")
    k = int(f.readline())
    centroids = []
    for j in range(k):
        pos = f.readline()
        pos = pos.split(",")
        pos = [float(x) for x in pos]
        for i in range(3):
            if i < 2:
                pos[i] = float(pos[i])
            else:
                pos[i] = int(pos[i])
        centroids.append(pos)
    lines = f.readlines()
    lst = []
    for line in lines:
        lst.append(int(line))
    

    df2 = pd.DataFrame(centroids, columns=['x','y','id'])
    df['label'] = lst

    fig = plt.figure()
    ax1 = fig.add_subplot(111)
    plt.title("k: "+str(k))
    ax1.scatter(list(df['x']), list(df['y']), s = 5, c=list(df['label'].apply(lambda x: colors[x-1])))
    ax1.scatter(list(df2['x']), list(df2['y']), s = 50, c=list(df2['id'].apply(lambda x: colors[x-1])))
    
    # plt.scatter(list(df['x1']), list(df['x2']), s = 5, c=list(df['categories'].apply(lambda x: colors[x-1])))
#plt.show()



#plt.scatter(list(df['x1']), list(df['x2']), s = 5, c=list(df['categories'].apply(lambda x: colors[x-1])))

#plt.show()

f = open(ptype+"Data/plot"+ptype+"Errors.csv")
errors = f.read()
errors = errors.split(" ")
errors.pop()
errors = [float(x) for x in errors]

fig = plt.figure()
plt.plot(clusters,errors)
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