# We've made libbayes a LIBRARY this time around, so that it's easier to link to
# your main program and your test executable. If you add any .cpp files or headers
# to libbayes, that code should get propagated to any CMake target that links to
# libbayes.
add_library(libbayes STATIC image.cpp model.cpp)

# Declare the location of the header files we want to expose to people who
# use this library. (If you DON'T want some header files to be publicly
# available, use target_include_directories(libbayes PRIVATE ...))
target_include_directories(libbayes PUBLIC ./)

if (${CMAKE_CXX_COMPILER_ID} STREQUAL "Clang"
        OR ${CMAKE_CXX_COMPILER_ID} STREQUAL "GNU")
    target_compile_options(libbayes PRIVATE
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
    target_compile_options(libbayes PRIVATE
            /W3)
endif ()
