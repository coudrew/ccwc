# Build Your Own `wc` Tool Coding Challenge
![GitLab CI status](https://gitlab.com/coudrew/ccwc/badges/release/pipeline.svg)

Part of John Crickett's [Coding Challenges](https://codingchallenges.fyi/challenges/challenge-wc)

## Overview
A Kotlin implementation of the wc Linux command line tool

## Features
- Count the number of lines in a file
- Count the number of words in a file
- Count the number of characters in a file
- Count the number of bytes in a file
- Accepts input via pipe

## Installation

1. **Clone the repository**:

    ```sh
    git clone https://gitlab.com/coudrew/ccwc.git
    cd ccwc
    ```
2. **Build the project**
    Run the gradle build step from Intellij, or from the terminal run:
    ```sh
    ./gradlew build
    ```

## Usage

The tool can be used similarly to the standard `wc` command:

```sh
./ccwc [OPTION]... [FILE]...
```

```sh
cat [FILE] | ./ccwc
```
### Options
    * -l: Print the line count
    * -w: Print the word count
    * -c: Print the byte count
    * -m: Print the character count
    * -h: Display help information