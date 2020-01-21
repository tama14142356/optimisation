import numpy as np
from scipy import linalg as la
TH = 0.000001
A = np.matrix([[6.0,1.0],[1.0,2.0]])
b = np.matrix([[2.0],[1.0]])
x = np.matrix([[0.0],[0.0]])
alpha = 0.5
for i in range(100):
    dx = A*x - b
    x = x - alpha * dx
    alpha = alpha*0.8
    if(dx.T*dx < TH):
        break
print(i, ':', x.T)
         
