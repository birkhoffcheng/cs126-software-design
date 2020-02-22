cmake_minimum_required(VERSION 3.1...3.15)
if (${CMAKE_VERSION} VERSION_LESS 3.12)
    cmake_policy(VERSION ${CMAKE_MAJOR_VERSION}.${CMAKE_MINOR_VERSION})
endif ()

project(sudoku
        LANGUAGES CXX)

# In a real CMake application you wouldn't enable a debug
# build like this, but for our purposes it's just fine.
#
# This tells the compiler to not aggressively optimize and
# to include debugging information so that the debugger
# can properly read what's going on.
set(CMAKE_BUILD_TYPE Debug)

add_subdirectory(src/)
add_subdirectory(test/)
