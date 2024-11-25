#!/usr/bin/env bash
# wait-for-it.sh: Script to wait for a service to be ready.

set -e

HOST=$1
PORT=$2
shift 2
CMD="$@"

echo "Waiting for $HOST:$PORT to be ready..."

while ! nc -z $HOST $PORT; do
  sleep 1
done

echo "$HOST:$PORT is available. Starting the application..."
exec $CMD
