#!/bin/bash

javaPath=`which java`

fullPath=${javaPath}:'/jre/lib/fonts'

echo ${fullPath}

mv ./fonts/SIMHEI.TTF  ${fullPath}

mv ./fonts/SIMKAI.TTF  ${fullPath}

mv ./fonts/simsun.ttf  ${fullPath}

