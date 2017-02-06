import h5py

from tflearn.data_utils import build_hdf5_image_dataset

count = 0
with open('data/filelist.txt', 'r') as istr:
    with open('output.txt', 'w') as ostr:
        for line in istr:

            line = 'data/' + line.replace(" ", "").rstrip('\n') + ' ' + str(count // 80)
            count += 1
            print(line, file=ostr)

#build_hdf5_image_dataset('output.txt', image_shape=(225, 225, 1), mode='file', output_path='dataset.h5', categorical_labels=True, grayscale=True)
h5f = h5py.File('dataset.h5', 'r')
X = h5f['X']
Y = h5f['Y']

print(X[2])
