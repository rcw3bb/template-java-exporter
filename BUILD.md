# Build

## Prerequisites

* [BuildTools for Visual Studio](https://visualstudio.microsoft.com/downloads/)
    * Install **Desktop development with C++**

* [GraalVM for Java 21](https://www.graalvm.org/downloads/)

## Testing

Run the following command to test the application:

```
gradlew clean check
```

> The preceding command must be run from the location where you've cloned the repository.

## Packaging Standalone Executable

Run the following command to build the application executable:

```
gradlew packImage
```

> The package will be available in the following directory:
>
> ```
> <REPO_DIR>\build\pack
> ```
>
> REPO_DIR is the location where you've cloned the repository.

## Packaging with Java

Run the following command to build the application with Java runtime:

```
gradlew packWin
```

> The package will be available in following directory:
>
> ```
> <REPO_DIR>\build\pack
> ```
>
> REPO_DIR is the location where you've cloned the repository.

## Locking Dependencies

Lock the dependencies to make the build reproducible using the following command:

```
gradlew dependencies --write-locks
```

The preceding command will create the lock files *(e.g. gradle.lockfile and settings-gradle.lockfile)* that must be part of the source control. If the lock files already exist, they will be overwritten.

> There are no lock files in this repository since it is a template. Always start the development as much as possible with the latest stable versions.
