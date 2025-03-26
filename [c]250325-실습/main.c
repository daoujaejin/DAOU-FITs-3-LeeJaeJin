#include "module.h"
#include "dbConnect.h"

int main() {
    get_db_data();

    while (1) {
        if (ask_choice() == 1)
            break;
    }

    update_db_data();
    save_csv();
    save_binary();
    free_transactions();
    return 0;
}