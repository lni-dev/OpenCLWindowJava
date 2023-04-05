cmake_minimum_required(VERSION 3.24)
project(OpenCLWindowJava VERSION 1.0.0 DESCRIPTION "OpenCL Rendering Library JNI Bindings")
include(UseJava)

add_link_options(-static-libgcc -static-libstdc++ -static -Wl,--kill-at)

set(CMAKE_CXX_STANDARD 23)

add_subdirectory(libs/OpenCLWindow)

find_package(Java REQUIRED)
find_package(JNI REQUIRED)


add_executable(test src/test/cpp/main.cpp)
add_custom_target(copy_resources ALL COMMAND ${CMAKE_COMMAND} -E copy_directory
        ${PROJECT_SOURCE_DIR}/src/test/resources
        ${PROJECT_BINARY_DIR}/src/test/resources
        COMMENT "Copying resources")
add_dependencies(test copy_resources)
target_link_libraries(test PUBLIC OpenCLWindow -static-libgcc -static-libstdc++ -Wl,--kill-at)


add_library(${PROJECT_NAME} MODULE
        src/main/cpp/de_linusdev_nat_openclwindow_OpenCLWindowJava.cpp
        src/main/cpp/OpenClWindowJava.cpp
        )

set_target_properties(${PROJECT_NAME} PROPERTIES SUFFIX ".jnilib"
        PUBLIC_HEADER include/de_linusdev_nat_openclwindow_OpenCLWindowJava.h)



target_link_libraries(${PROJECT_NAME}
        PRIVATE ${JNI_LIBRARIES}
        PUBLIC OpenCLWindow -static-libgcc -static-libstdc++
        )

include_directories(${JNI_INCLUDE_DIRS} ${_classDir} ${_stubDir} include)