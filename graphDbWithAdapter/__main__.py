import sys
import logging
import logstash
import random
from datetime import tzinfo, timedelta, datetime
"""
    graphDbWithAdapter.__main__
    ~~~~~~~~~~~~~~

    python -m graphDbWithAdapter seems to do the trick.

    :copyright: (c) 2015 by Jordan Woerndle.
    :license: MIT, see LICENSE for more details.
"""

__author__ = 'jordan'


def loadLogstash():
    host = 'localhost'

    print ('host was ' + host)
    test_logger = logging.getLogger('python-logstash-logger')
    test_logger.setLevel(logging.INFO)
    test_logger.addHandler(logstash.LogstashHandler(host, 5959, version=1))

    # test_logger.error('python-logstash: test logstash error message.')
    # test_logger.info('python-logstash: test logstash info message.')
    # test_logger.warning('python-logstash: test logstash warning message.')

    # add extra field to logstash message
    # mid atlantic water	aaa	1	4/10/15 10:35	98	0.022	0.54


    for d in range(1,30):
        print('day '+str(d))
        for h in range(24):
            print('hour '+str(h))

            extra = {
                'company': 'mid atlantic water',
                'shot': 'aaa;',
                'segment': 1,
                'location': '41.827163,-71.401590',
                'temperature': random.uniform(95, 110),
                'pressure': random.uniform(0.01, 0.3),
                'flow': random.uniform(0.45, 0.55),
                'timeDate': datetime(2015, 4, d, h, 30).strftime('%Y-%m-%dT%I:%M:00')
            }
            test_logger.info('segment', extra=extra)

    extra = {
        'company': 'mid atlantic water',
        'shot': 'aaa;',
        'segment': 1,
        'location': '41.827163,-71.401590',
        'temperature': random.uniform(95, 110),
        'pressure': random.uniform(0.01, 0.3),
        'flow': random.uniform(0.45, 0.55)
    }
    test_logger.info('segment', extra=extra)


def main(argv=()):
    """
    Args:
        argv (list): List of arguments
    Returns:
        int: A return code
    Does stuff.
    """
    print('hello')
    loadLogstash()
    print(argv)
    return 0

if __name__ == "__main__":
    sys.exit(main())


