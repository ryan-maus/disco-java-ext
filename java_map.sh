#!/bin/sh

# This is the path to the jar on ALL nodes
OS_DISCO=/data/4/disco/rmaus/disco-java-ext.jar

java -cp $OS_DISCO rmaus.disco.external.map.RunDiscoMap
