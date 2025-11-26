# JVM Architecture Report

This document explains the internal architecture of the **Java Virtual Machine (JVM)** including class loading, runtime
areas, execution engine, and the "Write Once, Run Anywhere" principle.

---

## 1. Class Loader

The Class Loader subsystem loads `.class` bytecode files into JVM memory.

### Responsibilities:

- **Loading**: Finds class files from file system, JARs, classpath, network.
- **Linking**:
    - Verification (checks bytecode correctness)
    - Preparation (allocates memory for static variables)
    - Resolution (replaces symbolic references)
- **Initialization**: Runs static initializers.

---

## 2. JVM Runtime Data Areas

JVM divides memory into several runtime regions:

### ### 2.1 Heap (Shared)

- Stores objects and arrays.
- Garbage-collected.
- Largest memory region.

### ### 2.2 Stack (Per Thread)

- Stores:
    - method frames
    - local variables
    - operand stacks
- Created per thread.

### ### 2.3 Method Area (Shared)

- Stores:
    - Class data
    - Method metadata
    - Runtime constant pool

### ### 2.4 PC Register (Per Thread)

- Holds address of currently executing bytecode instruction.

### ### 2.5 Native Method Stack

- For native code (C/C++ via JNI).

---

## 3. Execution Engine

The component that executes bytecode.

Contains:

### 3.1 Interpreter

- Reads one bytecode instruction at a time.
- Fast startup, slow execution.

### 3.2 JIT Compiler (Just-In-Time)

- Converts frequently used bytecode into native machine code.
- Much faster after optimization.

### 3.3 Garbage Collector (GC)

- Automatically deallocates unused objects.
- Uses algorithms like Mark-Sweep, G1, ZGC.

---

## 4. Interpreter vs JIT Compiler

| Feature      | Interpreter  | JIT Compiler            |
|--------------|--------------|-------------------------|
| Execution    | Line-by-line | Converts to native code |
| Speed        | Slow         | Fast after warm-up      |
| Startup time | Fast         | Slow startup            |
| Optimization | None         | Heavy optimizations     |

Modern JVM uses **both** for optimal performance.

---

## 5. "Write Once, Run Anywhere"

Java achieves portability through:

- Programs compiled to **platform-independent bytecode**.
- Bytecode executed by JVMs available on all OS/hardware.
- JVM abstracts OS-specific details.

**Conclusion:** same `.class` file runs on Windows, Mac, Linux without changes.

---
