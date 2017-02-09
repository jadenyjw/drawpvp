from __future__ import print_function
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

batch_size = 135
nb_classes = 250
nb_epoch = 600

# input image dimensions
img_rows, img_cols = 225, 225

X_train = np.asarray(HDF5Matrix('training.h5', 'X'))
Y_train = np.asarray(HDF5Matrix('training.h5', 'Y'))

X_test = np.asarray(HDF5Matrix('test.h5', 'X'))
Y_test = np.asarray(HDF5Matrix('test.h5', 'Y'))

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

datagen = ImageDataGenerator(featurewise_center=True,
    featurewise_std_normalization=True,
    rotation_range=5,
    width_shift_range=0.14,
    height_shift_range=0.14,
    horizontal_flip=True)
datagen_test = ImageDataGenerator(featurewise_center=True,
    featurewise_std_normalization=True)

datagen.fit(X_train)
datagen_test.fit(X_test)
# convert class vectors to binary class matrices

#Y_train = np_utils.to_categorical(y_train, nb_classes)
#Y_test = np_utils.to_categorical(y_test, nb_classes)

model = Sequential()

#L1
model.add(Convolution2D(nb_filter=64, nb_row=15, nb_col=15,
                        border_mode='valid',
                        input_shape=input_shape,
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
sgd = SGD(lr=0.003, decay=1e-6, momentum=0.9, nesterov=True)
model.compile(loss='categorical_crossentropy',
              optimizer=sgd,
              metrics=['accuracy'])
checkpointer = ModelCheckpoint(filepath="checkpoint.hdf5", verbose=1, save_best_only=True)
tensorboard = TensorBoard(log_dir='logs', histogram_freq=0, write_graph=True, write_images=False)
model.fit_generator(datagen.flow(X_train, Y_train, batch_size=135), samples_per_epoch=18000, nb_epoch=nb_epoch,
          verbose=1, validation_data=datagen_test.flow(X_test, Y_test), callbacks=[checkpointer, tensorboard], nb_val_samples=2000)
score = model.evaluate(X_test, Y_test, verbose=0)
model.save('drawpvp.h5')

print('Test score:', score[0])
print('Test accuracy:', score[1])
