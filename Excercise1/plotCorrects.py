import matplotlib.pyplot as plt



f = open("out.txt")
nums = f.read()
y = nums.split(' ')
y.pop()

y = [float(i) for i in y]
x = list(range(len(y)))


plt.figure(figsize=(15,15))
plt.tight_layout()
plt.plot(x,y)

plt.show()

# print(x)
# print()
# print(y)

