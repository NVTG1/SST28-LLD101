# Distributed Cache System

## Overview

This project implements a simple **distributed cache system** with multiple cache nodes.
It supports basic operations like `get` and `put`, along with **LRU eviction** and **pluggable distribution strategy**.

---

## Features

* Multiple cache nodes (configurable)
* Key distribution across nodes
* Cache-aside pattern for cache miss
* LRU (Least Recently Used) eviction
* Extensible design using interfaces

---

## How It Works

### 1. Data Distribution

* Keys are distributed using a **modulo-based strategy**:

  ```
  index = hash(key) % number_of_nodes
  ```
* Ensures even and deterministic distribution.

---

### 2. Get Operation

* Check cache node for the key
* If found → return value (**cache hit**)
* If not found → fetch from database, store in cache, return value (**cache miss**)

---

### 3. Put Operation

* Store key-value in the correct cache node
* Also update the database (write-through)

---

### 4. Eviction Policy

* Each node has limited capacity
* Uses **LRU**:

  * Removes least recently used item when full

---

## Design

### Components

* `DistributedCache` → main controller
* `CacheNode` → stores data
* `DistributionStrategy` → decides node
* `EvictionPolicy` → handles eviction
* `Database` → mock storage

---

## Extensibility

* Can add new distribution strategies (e.g., consistent hashing)
* Can add new eviction policies (e.g., LFU, MRU)
* No changes required in core classes

---

## Assumptions

* Keys are unique
* In-memory implementation
* No network communication between nodes

---

## Example

```java
cache.put("1", "Science");
cache.get("1"); // cache hit
cache.get("5"); // cache miss → DB fetch
```
