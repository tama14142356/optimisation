import numpy as np
from scipy import linalg as la

x = np.matrix([[-2,1],[-1,1],[0,1],[1,1],[2,1]]) # 本物のD
y = np.matrix([0,-1,1,3,2])
y = y.T # 本来のyベクトル
D = x.T*x #D^T*Dを表す
e = np.matrix([1, 2])
w = e*la.inv(D)*x.T*y

print(w)
