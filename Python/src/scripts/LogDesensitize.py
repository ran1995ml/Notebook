#!/usr/bin/python3
import re
import argparse
import os
'''
--key: the keys that need to be desensitized in the file, support multiple input,eg:view_id
--path: the path of the file, eg:./log/ applies to all files in the directory; eg:./log/log1 applies to the specified file
'''

parser = argparse.ArgumentParser('the keys should be desensibilisation')
parser.add_argument('--key',nargs='*',default=['view.id'])
parser.add_argument('--path',nargs='*',default=['./'])
args = parser.parse_args()

# desensitize the specified key in a file
def replaceFileKeyValue(path):
    print("desensitizing file:"+path)
    with open(path,'r+') as f:
        file = f.read()
        for key in args.key:
            search_pattern = '\"dimension\":\"{0}\",\"value\":\"[^\"]+\"'.format(key)
            replace_pattern = '\"dimension\":\"{0}\",\"value\":\"*\"'.format(key)
            file = re.sub(search_pattern,replace_pattern,file)
            f.seek(0)
            f.write(file)
            f.truncate()

# desensitize the specified key of all files in a directory
def replaceDirKeyValue(path):
    for dir,subdirs,files in os.walk(path):
        for file in files:
            path = dir + file
            replaceFileKeyValue(path)

if __name__ == "__main__":
    for path in args.path:
        if os.path.isdir(path):
            replaceDirKeyValue(path)
        elif os.path.isfile(path):
            replaceFileKeyValue(path)
        else:
            print('invalid input!')