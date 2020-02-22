# The .cpp files for this executable
add_executable(sudoku
		sudoku.cpp
		main.cpp)

# Directories to search for header files
 target_include_directories(sudoku PRIVATE
        )

set_target_properties(sudoku PROPERTIES
        CXX_STANDARD 11
        CXX_STANDARD_REQUIRED ON
        CXX_EXTENSIONS OFF)

# Cross-platform compiler lints
if (${CMAKE_CXX_COMPILER_ID} STREQUAL "Clang"
        OR ${CMAKE_CXX_COMPILER_ID} STREQUAL "GNU")
    target_compile_options(sudoku PRIVATE
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
    target_compile_options(sudoku PRIVATE
            /W3)
endif ()
