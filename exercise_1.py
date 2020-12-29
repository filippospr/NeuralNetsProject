import random
import matplotlib.pyplot as plt
import numpy as np
def categorize(x1,x2):
	category_random=random.random()
	if x1**2+x2**2<0.25 and category_random<=0.9:
		return 1
	elif -1<=x1<=-0.4 and -1<=x2<=-0.4 and category_random<=0.9:
		return 2
	elif 0.4<=x1<=1 and 0.4<=x2<=1 and category_random<=0.9:
		return 2
	elif -1<=x1<=-0.4 and 0.4<=x2<=-1 and category_random<=0.9:
		return 3
	elif 0.4<=x1<=1 and -1<=x2<=-0.4 and category_random<=0.9:
		return 3
	return 4
def createRandomPoints():
    x1=[]
    x2=[]
    category=[]
    for i in range(6000):
    	x = random.uniform(-1,1)
    	y = random.uniform(-1,1)
    	x1.append(x)
    	x2.append(y)
    	category.append(categorize(x,y))

    return [x1,x2,category]

def writeToFile(points):
	file=open("results.csv",'w')
	for i in range(6000):
		file.write(str(points[0][i])+","+str(points[1][i])+","+str(points[2][i])+"\n")
	file.close()


points = np.array(createRandomPoints())
print(points.shape)
categories=points[2,:]
colormap = np.array(['r','g','b','c'])

fig, ax = plt.subplots()
plt.scatter(points[0,:] , points[1,:], c=colormap[categories])
plt.show()


'''

points = createRandomPoints()
points = np.array(points)
categories=points[:,2]
points=np.array([points[:,0],points[:,1]])
print(points)
# print(points)

plt.scatter(points[0] , points[1], c=colormap[categories])


# writeToFile()


'''