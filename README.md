# Besu Plugins relating to tracer and sequencer functionality

A Linea tracing implementation for [Hyperledger Besu](https://github.com/hyperledger/besu) based on
an [existing implementation in Go](https://github.com/Consensys/zk-evm/).

## Quickstart - Running Besu with Linea Plugins

- compile linea-plugins `gradlew installDist`
- copy jar file to besu runtime plugins/ directory (where you will run besu from, not where you're building besu)
- add `ROLLUP` to besu config to enable the plugin RPC methods
  - rpc-http-api=\["ADMIN","ETH","NET","WEB3","ROLLUP"\]
- start besu (command line or from IDE) and you should see plugins registered at startup
- call the RPC endpoint eg

```shell
  curl --location --request POST 'http://localhost:8545' --data-raw '{
    "jsonrpc": "2.0",
    "method": "rollup_generateConflatedTracesToFileV0",
    "params": [0, 0, "6.16.0"],
    "id": 1
  }'
```

## Development Setup

### Install Java 17

```
brew install openjdk@17
```

### Native Lib Prerequisites

Linux/MacOs
* Install the relevant CGo compiler for your platform
* Install the Go toolchain

Windows
* Requirement [Docker Desktop WSL 2 backend on Windows](https://docs.docker.com/desktop/wsl/)

On release native libs are build for all the supported platforms,
if you want to test this process locally run `./gradlew -PreleaseNativeLibs jar`,
jar is generated in `arithmetization/build/libs`.

### Install Rust

```
curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh

# Use local git executable to fetch from repos (needed for private repos)
echo "net.git-fetch-with-cli=true" >> .cargo/config.toml
```

### Install Corset

```shell
cargo install --git ssh://git@github.com/Consensys/corset
```

### Update Constraints [Submodule](https://github.com/Consensys/zkevm-constraints/)

```shell
git submodule update --init --recursive
```

### Install [pre-commit](https://pre-commit.com/)

```shell
pip install --user pre-commit

# For macOS users.
brew install pre-commit
```

Then run `pre-commit install` to set up git hook scripts.
Used hooks can be found [here](.pre-commit-config.yaml).

______________________________________________________________________

NOTE

> `pre-commit` aids in running checks (end of file fixing,
> markdown linting, linting, runs tests, json validation, etc.)
> before you perform your git commits.

______________________________________________________________________

### Run tests

```shell
# Run all tests
./gradlew clean test

# Run only unit tests
./gradlew clean unitTests

# Run only acceptance tests
./gradlew clean acceptanceTests

# Run EVM test suite BlockchainTests
./gradlew clean referenceBlockchainTests

# Run EVM test suite GeneralStateTests
./gradlew clean referenceGeneralStateTests

# Run all EVM test suite reference tests
./gradlew clean referenceTests

# Run single reference test via gradle, e.g for net.consensys.linea.generated.blockchain.BlockchainReferenceTest_583
./gradlew :reference-tests:referenceTests --tests "net.consensys.linea.generated.blockchain.BlockchainReferenceTest_583"
```

______________________________________________________________________

NOTE

> Please be aware if the reference test code generation tasks `blockchainReferenceTests` and
> `generalStateReferenceTests` do not generate any java code, than probably you are missing the Ethereum tests
> submodule which you can clone via `git submodule update --init --recursive`.

______________________________________________________________________

### Capturing a replay

For debugging and inspection purposes, it is possible to capture a _replay_, _i.e._ all the minimal information required to replay a series of blocks as they played on the blockchain, which is done with `scripts/capture.pl`.

A typical invocation would be:

```
scripts/capture.pl --start 1300923
```

which would capture a replay of block #1300923 and store it in `arithmetization/src/test/resources/replays`. More options are available, refer to `scripts/capture.pl -h`.

## IntelliJ IDEA Setup

### Enable Annotation Processing

- Go to `Settings | Build, Execution, Deployment | Compiler | Annotation Processors` and tick the following
  checkbox:

  ![idea_enable_annotation_processing_setting.png](images/idea_enable_annotation_processing_setting.png)

______________________________________________________________________

NOTE

> This setting is required to avoid IDE compilation errors because of the [Lombok](https://projectlombok.org/features/)
> library used for code generation of boilerplate Java code such as:
>
> - Getters/Setters (via [`@Getter/@Setter`](https://projectlombok.org/features/GetterSetter))
> - Class log instances (via [`@Slf4j`](https://projectlombok.org/features/log))
> - Builder classes (via [`@Builder`](https://projectlombok.org/features/Builder))
> - Constructors (
>   via [`@NoArgsConstructor/@RequiredArgsConstructor/@AllArgsConstructor`](https://projectlombok.org/features/constructor))
> - etc.
>
> Learn more about how Java annotation processing
> works [here](https://www.baeldung.com/java-annotation-processing-builder).

______________________________________________________________________

### Set Up IDE Code Re-formatting

- Install [Checkstyle](https://plugins.jetbrains.com/plugin/1065-checkstyle-idea) plugin and set IDE code
  reformatting to comply with the project's Checkstyle configuration:

  - Go to `Settings | Editor | Code Style | Java | <hamburger menu> | Import Scheme | Checkstyle configuration`:

    ![idea_checkstyle_reformat.png](images/idea_checkstyle_reformat.png)

    and select `<project_root>/config/checkstyle.xml`.

### Install Optional Plugins

- Install [Spotless Gradle](https://plugins.jetbrains.com/plugin/18321-spotless-gradle) plugin to re-format through
  the IDE according to spotless configuration.

## Debugging Traces

- JSON files can be debugged with the following command:

```shell
corset check -T <JSON_FILE> -v zkevm-constraints/zkevm.bin
```

## Plugins

Plugins are documented [here](PLUGINS.md).

## Release Process
Here are the steps for releasing a new version of the plugins:
  1. Create a tag with the release version number in the format vX.Y.Z (e.g., v0.2.0 creates a release version 0.2.0).
  2. Push the tag to the repository.
  3. GitHub Actions will automatically create a draft release for the release tag.
  4. Once the release workflow completes, update the release notes, uncheck "Draft", and publish the release.

Note: Release tags (of the form v*) are protected and can only be pushed by organization and/or repository owners.
