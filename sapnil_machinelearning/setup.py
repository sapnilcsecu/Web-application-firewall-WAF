"""Setup script for realpython-reader"""

import os.path
from setuptools import setup

# The directory containing this file
HERE = os.path.abspath(os.path.dirname(__file__))

# The text of the README file
with open(os.path.join(HERE, "README.md")) as fid:
    README = fid.read()

# This call to setup() does all the work
setup(
    name="sapnil_machinelearning",
    version="1.0.0",
    description="machinelearning API",
    long_description=README,
    long_description_content_type="text/markdown",
    url="https://github.com/sapnilcsecu/sapnil_machinelearning.git",
    author="Nasir uddin",
    author_email="nasircsecu@gmail.com",
    license="GNU",
    classifiers=[
        "License :: OSI Approved :: GNU License",
        "Programming Language :: Python",
        "Programming Language :: Python :: 2",
        "Programming Language :: Python :: 3",
    ],
    #packages=["reader","reader.feature_eng","sapnil_machinelearning","sapnil_machinelearning.classifier","sapnil_machinelearning.dataset_pre","sapnil_machinelearning.feature_eng","sapnil_machinelearning.model"],
    packages=["classifier","dataset_pre","feature_eng","model"],
	include_package_data=True,
    install_requires=[
        'pandas','numpy','nltk'
    ],
    entry_points={"console_scripts": ["realpython=reader.__main__:main"]},
)
