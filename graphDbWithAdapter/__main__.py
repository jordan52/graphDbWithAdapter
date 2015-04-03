import sys
"""
    graphDbWithAdapter.__main__
    ~~~~~~~~~~~~~~

    python -m graphDbWithAdapter seems to do the trick.

    :copyright: (c) 2015 by Jordan Woerndle.
    :license: MIT, see LICENSE for more details.
"""

__author__ = 'jordan'

def main(argv=()):
    """
    Args:
        argv (list): List of arguments
    Returns:
        int: A return code
    Does stuff.
    """
    print('hello')
    print(argv)
    return 0

if __name__ == "__main__":
    sys.exit(main())


