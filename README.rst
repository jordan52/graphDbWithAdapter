===============================
graphDbWithAdapter
===============================

.. image:: https://img.shields.io/travis/jordan52/graphDbWithAdapter.svg
        :target: https://travis-ci.org/jordan52/graphDbWithAdapter

.. image:: https://img.shields.io/pypi/v/graphDbWithAdapter.svg
        :target: https://pypi.python.org/pypi/graphDbWithAdapter


half-baked idea dump (now in python!)

GraphDbWithAdapter is a meaningless name for a project that serves as a dump for various half-baked ideas in NLP and machine learning.

Highlights include:

* Free software: MIT license
* Documentation: https://graphDbWithAdapter.readthedocs.org.

Bootstrapped with Cookiecutter
------------------------------

To get everything (pip install, testing, etc.) working properly, reference:
https://github.com/audreyr/cookiecutter-pypackage
https://github.com/ionelmc/cookiecutter-pylibrary
https://github.com/ionelmc/python-nameless

Features
--------

* None

Use
---

* clone
* cd graphDbWithAdapter
* virtualenv venv
* source venv/bin/activate
* pip install -r requirements.txt
* python -m unittest
* python -m graphDbWithAdapter

Test
----

To run all the tests, just run:

tox
To see all the tox environments:

tox --listenvs
To only build the docs:

tox -e docs
To build and verify that the built package is proper and other code QA checks:

tox -e check
Releasing the project

Before releasing your package on PyPI you should have all the tox environments passing.

To make a release of the project on PyPI, the most simple usage is:

python setup.py release
twine upload dist/*
Explanations:

release is aliased to register clean sdist bdist_wheel, see setup.cfg.
twine is a tool that you can use to securely upload your releases to PyPI.
If you care about security you can do secure uploads to PyPI using twine.

