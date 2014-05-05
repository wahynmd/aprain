#!/bin/sh
ls -1 /root/logs/apache_log_*|sort|head --lines=-7|xargs rm -f

