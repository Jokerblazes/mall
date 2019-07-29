#!/bin/bash
function compare_two_branchs()
{
    branch1_diff_branch2=`git log $1..$2`
    branch2_diff_branch1=`git log $2..$1`
    if [ -z "$branch1_diff_branch2" ] && [ -n "$branch2_diff_branch1" ]; then 
    echo "主干分支有更新并且分支没有新提交！" 
    return 0
    fi
    return 1
}

show_menu
compare_two_branchs master release
echo $1



