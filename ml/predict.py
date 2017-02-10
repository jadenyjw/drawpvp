import numpy as np

from matplotlib import pyplot as plt
from keras.models import Sequential
from keras.layers import Dense, Dropout, Activation, Flatten, Reshape
from keras.layers import Convolution2D, MaxPooling2D, ZeroPadding2D, Convolution1D
from keras.utils import np_utils
from keras.utils.io_utils import HDF5Matrix
from keras import backend as K
from keras.preprocessing.image import ImageDataGenerator
from keras.optimizers import SGD
from keras.callbacks import ModelCheckpoint
from keras.callbacks import TensorBoard
from scipy import misc

from PIL import Image

batch_size = 135
nb_classes = 250
nb_epoch = 600


# input image dimensions
img_rows, img_cols = 225, 225

model = Sequential()

#L1
model.add(Convolution2D(nb_filter=64, nb_row=15, nb_col=15,
                        border_mode='valid',
                        input_shape=[225, 225, 1],
                        activation='relu',
                        subsample=(3,3)))
#model.add(ZeroPadding2D(padding=(0,0)))
model.add(MaxPooling2D(pool_size=(3,3), strides=(2,2)))

#L2
model.add(Convolution2D(nb_filter=128, nb_row=5, nb_col=5,
                        activation='relu',
                        subsample=(1,1)))
#model.add(ZeroPadding2D(padding=(0,0)))
model.add(MaxPooling2D(pool_size=(3,3), strides=(2,2)))

#L3
model.add(Convolution2D(nb_filter=256, nb_row=3, nb_col=3,
                        activation='relu', border_mode='same',
                        subsample=(1,1)))
#model.add(ZeroPadding2D(padding=(1,1)))

#L4
model.add(Convolution2D(nb_filter=256, nb_row=3, nb_col=3,
                        activation='relu', border_mode='same',
                        subsample=(1,1)))
#model.add(ZeroPadding2D(padding=(1,1)))

#L5
model.add(Convolution2D(nb_filter=256, nb_row=3, nb_col=3,
                        activation='relu', border_mode='same',
                        subsample=(1,1)))
#model.add(ZeroPadding2D(padding=(1,1)))

model.add(MaxPooling2D(pool_size=(3,3), strides=(2,2)))
#L6
model.add(Convolution2D(nb_filter=512, nb_row=7, nb_col=7,
                        activation='relu',
                        subsample=(1,1)))
#model.add(ZeroPadding2D(padding=(0,0)))

model.add(Dropout(0.5))
#L7
model.add(Convolution2D(nb_filter=512, nb_row=1, nb_col=1,
                        activation='relu',
                        subsample=(1,1)))
#model.add(ZeroPadding2D(padding=(0,0)))

model.add(Dropout(0.5))

model.add(Convolution2D(nb_filter=250, nb_row=1, nb_col=1,
                        activation='relu',
                        subsample=(1,1)))
#model.add(ZeroPadding2D(padding=(0,0)))
model.add(Flatten())
model.add(Activation("softmax"))
model.load_weights('drawpvp.h5')

img = Image.open('apple.png').convert('L')

data = np.asarray(img).reshape([-1, 225, 225, 1])


data = (data - np.mean(data))/np.std(data)

print(model.predict(data, verbose=1).argmax())
