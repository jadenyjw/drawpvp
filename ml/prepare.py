import h5py

from tflearn.data_utils import build_hdf5_image_dataset

count = 0
with open('data/filelist.txt', 'r') as istr:
    with open('train.txt', 'w') as ostr:
        with open('test.txt', 'w') as pstr:

            for line in istr:
                line = 'data/' + line.replace(" ", "").rstrip('\n') + ' ' + str(count // 80)
                if count % 10 == 0:
                    print(line, file=pstr)
                else:
                    print(line, file=ostr)

                count += 1

build_hdf5_image_dataset('train.txt', image_shape=(225, 225, 1), mode='file', output_path='training.h5', categorical_labels=True, grayscale=True)
build_hdf5_image_dataset('test.txt', image_shape=(225, 225, 1), mode='file', output_path='test.h5', categorical_labels=True, grayscale=True)

count = 0
with open('test.txt', 'r') as istr:
    with open('../src/main/resources/text/dictionary.txt', 'w') as pstr:
        for line in istr:
            if count % 10 == 0:
                print(line, file=pstr)
            count +=1
