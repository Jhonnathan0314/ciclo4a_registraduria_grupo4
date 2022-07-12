from abc import ABCMeta;

class AbstractModel(metaclass=ABCMeta):
    def __init__(self, data):
        for k, v, in data.items():
            setattr(self, k, v)