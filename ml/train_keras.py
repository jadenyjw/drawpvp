from __future__ import print_function
import numpy as np

import matplotlib
import matplotlib.pyplot as plt
import matplotlib.image as mpimg
from matplotlib import pyplot as plt
from keras.models import Sequential
from keras.layers import Dense, Dropout, Activation, Flatten
from keras.layers import Convolution2D, MaxPooling2D, ZeroPadding2D
from keras.utils import np_utils
from keras.utils.io_utils import HDF5Matrix
from keras import backend as K
from keras.preprocessing.image import ImageDataGenerator
batch_size = 135
nb_classes = 250
nb_epoch = 100

# input image dimensions
img_rows, img_cols = 225, 225

X_train = np.asarray(HDF5Matrix('training.h5', 'X'))
Y_train = np.asarray(HDF5Matrix('training.h5', 'Y'))

X_test = np.asarray(HDF5Matrix('test.h5', 'X'))
Y_test = np.asarray(HDF5Matrix('test.h5', 'Y'))





# number of convolutional filters to use
# the data, shuffled and split between train and test sets
#(X_train, y_train), (X_test, y_test) = mnist.load_data()

if K.image_dim_ordering() == 'th':
    X_train = X_train.reshape(X_train.shape[0], 1, img_rows, img_cols)
    X_test = X_test.reshape(X_test.shape[0], 1, img_rows, img_cols)
    input_shape = (1, img_rows, img_cols)
else:
    X_train = X_train.reshape(X_train.shape[0], img_rows, img_cols, 1)
    X_test = X_test.reshape(X_test.shape[0], img_rows, img_cols, 1)
    input_shape = (img_rows, img_cols, 1)

X_train = X_train.astype('float32')
X_test = X_test.astype('float32')

print('X_train shape:', X_train.shape)
print(X_train.shape[0], 'train samples')
print(X_test.shape[0], 'test samples')

datagen = ImageDataGenerator()
datagen.fit(X_train)


# convert class vectors to binary class matrices

#Y_train = np_utils.to_categorical(y_train, nb_classes)
#Y_test = np_utils.to_categorical(y_test, nb_classes)

model = Sequential()

model.add(Convolution2D(64, 15, 15,
                        border_mode='valid',
                        input_shape=[225, 225, 1],
                        activation='relu',
                        subsample=(3,3)))
#model.add(ZeroPadding2D(padding=(0,0), dim_ordering='default'))
model.add(MaxPooling2D(pool_size=(3,3), strides=(2,2)))

model.add(Convolution2D(128, 5, 5,
                        activation='relu',
                        subsample=(1,1)))
model.add(ZeroPadding2D(padding=(0,0), dim_ordering='default'))
model.add(MaxPooling2D(pool_size=(3,3), strides=(2,2)))


model.add(Convolution2D(256, 3, 3,
                        activation='relu',
                        subsample=(1,1)))
model.add(ZeroPadding2D(padding=(1,1), dim_ordering='default'))
model.add(Convolution2D(256, 3, 3,
                        activation='relu',
                        subsample=(1,1)))
model.add(ZeroPadding2D(padding=(1,1), dim_ordering='default'))
model.add(Convolution2D(256, 3, 3,
                        activation='relu',
                        subsample=(1,1)))
model.add(ZeroPadding2D(padding=(1,1), dim_ordering='default'))
model.add(MaxPooling2D(pool_size=(3,3), strides=(2,2)))

model.add(Convolution2D(512, 7, 7,
                        activation='relu',
                        subsample=(1,1)))
model.add(ZeroPadding2D(padding=(0,0), dim_ordering='default'))

model.add(Dropout(0.5))

model.add(Convolution2D(512, 1, 1,
                        activation='relu',
                        subsample=(1,1)))
model.add(ZeroPadding2D(padding=(0,0), dim_ordering='default'))

model.add(Dropout(0.5))

model.add(Convolution2D(250, 1, 1,
                        activation='softmax',
                        subsample=(1,1)))

model.compile(loss='categorical_crossentropy',
              optimizer='adadelta',
              metrics=['accuracy'])

model.fit_generator(datagen.flow(X_train, Y_train, batch_size=135), nb_epoch=nb_epoch,
          verbose=1, validation_data=(X_test, Y_test))
score = model.evaluate(X_test, Y_test, verbose=0)
model.save('my_model.h5')

print('Test score:', score[0])
print('Test accuracy:', score[1])
