#pragma once
#ifndef DBCONNECT_H
#define DBCONNECT_H

#define _CRT_SECURE_NO_WARNINGS

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <oci.h>
#include <time.h>
#include "module.h"

void get_db_data();
void update_db_data();

#endif