#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
test_graphDbWithAdapter
----------------------------------

Tests for `graphDbWithAdapter` module.
"""

from graphDbWithAdapter.__main__ import main

def test_main():
    assert main([]) == 0
