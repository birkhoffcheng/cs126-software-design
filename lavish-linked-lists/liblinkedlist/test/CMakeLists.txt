add_executable(liblinkedlist-test test.cpp main.cpp)
target_include_directories(liblinkedlist-test PRIVATE ./)

target_link_libraries(liblinkedlist-test)

if (${CMAKE_CXX_COMPILER_ID} STREQUAL "Clang"
        OR ${CMAKE_CXX_COMPILER_ID} STREQUAL "GNU")
    target_compile_options(liblinkedlist-test PRIVATE
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
    target_compile_options(liblinkedlist-test PRIVATE
            /W3)
endif ()
