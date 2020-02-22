cmake_minimum_required(VERSION 3.1...3.15)
if (${CMAKE_VERSION} VERSION_LESS 3.12)
    cmake_policy(VERSION ${CMAKE_MAJOR_VERSION}.${CMAKE_MINOR_VERSION})
endif ()

project(NaiveBayes
        LANGUAGES CXX)

# In a real CMake application you wouldn't enable a debug
# build like this, but for our purposes it's just fine.
#
# This tells the compiler to not aggressively optimize and
# to include debugging information so that the debugger
# can properly read what's going on.
set(CMAKE_BUILD_TYPE Debug)

add_executable(naivebayes main.cpp)
target_include_directories(naivebayes PRIVATE ./)

add_subdirectory(libbayes/)
target_link_libraries(naivebayes libbayes)

# These settings will apply to the current directory and all subdirectories
set_target_properties(libbayes libbayes-tests naivebayes PROPERTIES
        CXX_STANDARD 11
        CXX_STANDARD_REQUIRED ON
        CXX_EXTENSIONS OFF)

if (${CMAKE_CXX_COMPILER_ID} STREQUAL "Clang"
        OR ${CMAKE_CXX_COMPILER_ID} STREQUAL "GNU")
    target_compile_options(naivebayes PRIVATE
            -Wall
            -Wextra
            -Wswitch
            -Wconversion
            -Wparentheses
            -Wfloat-equal
            -Wzero-as-null-pointer-constant
            -Wpedantic
            -pedantic
            -pedantic-errors)
elseif (${CMAKE_CXX_COMPILER_ID} STREQUAL "MSVC")
    target_compile_options(naivebayes PRIVATE
            /W3)
endif ()
