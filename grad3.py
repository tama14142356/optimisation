import numpy as np
from scipy import linalg as la
TH = 0.000001
A = np.array([[6.0,1.0],[1.0,2.0]])
b = np.array([[2.0],[1.0]])
x = np.array([[0.0],[0.0]])
e = 0.00001
p = 0.7
g = 0
for i in range(100):
    dx = A.dot(x) - b
    dx2 = dx*dx
    g = p*g + (1-p) * dx2
    alpha = 0.1 / np.sqrt(g+e)
    x = x - alpha * dx
    if(dx.dot(dx) < TH):
        break
print(i, ':', x.T)
         
