import matplotlib
matplotlib.use("TkAgg")
import matplotlib.pyplot as plt
import matplotlib.image as mpimg
import h5py
h5f = h5py.File('training.h5', 'r')
X = h5f['X']
Y = h5f['Y']

plt.imshow(X[10], interpolation='nearest',cmap='gray')
plt.show()
