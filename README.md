# Godfather - A Distributed, Highly-Available Key-Value Data Storage

Godfather is a learning project that I am undertaking. Inspired by existing KV Stores such as Dynamo, Voldemort, etcd.

The aim of this project is to learn the intricacies of highly scalable distributed data stores by implementing.

## Features

Godfather has high ambitions. It will aim to have the following properties:

1. Eventually Consistent
2. Highly Available via replication and clustering
3. Support Object Versioning
4. Highly concurrent reads and writes


## Inner workings

Since the aim of Godfather is to learn and teach, I will document the internal architecture and workings in
a thorough manner. 

Right now, at the beginning of the project, the goal is the following:

1. Have a data storage interface with pluggable data engines.
2. Write data engines for in-memory and on-disk data storage.
3. Have a gRPC-based server, and a corresponding client CLI interface.
4. Have a basic implementation of consistent hashing algorithm in order to support clustering in the future.

