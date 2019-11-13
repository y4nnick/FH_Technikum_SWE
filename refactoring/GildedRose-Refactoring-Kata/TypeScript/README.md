### Steps

 - Added newlines and comments to better understand the code
 - Added tests for all the requirements
 - After each of the following steps i executed the tests to see if i broke something
 - Replaced the hardcoded names with constants
 - Changed to forEach loop to not have index accesses on the array
    - Instead we have a var `item` now. Makes it more readable.
 - Replaced `value = value + 1` with `value += 1` etc. This is more readable IMHO.
 - Add early exit guard for `Sulfuras` (and remove all the checks later on) so that we don't have to thing about it any more
 - Move the sell in reduction to the top
 - Move all the logic for normal items (no special features) to the top and exit early
 - Add a comment to each line below for which item-type the line gets executed
 - Move all the logic for the special item `Aged Brie` up and early exit
 - Update all comments below to know which line gets executed for the last special item `Backstage pass`
 - Backstage pass:
    - Remove step by step the not needed parts of logic below the `Aged Brie` handling until we have the core logic of the `Backstage pass`
    - Step by step remove the double checks for `sellIn < 50` etc. to decrease nesting level.
    - Move the check for `sellIn < 0` to the top and early exit
    - Change `item.sellIn < 11` to `item.sellIn <= 10` since we would like to use the exact numbers from the requirements
    - Calculate the new quality in an own var and only check once at the end if we have more than 50
 - By removing the early exits we can check at the bottom once for invalid qualities
 - This way we are sure that we have all checks in one place
    but we sacrifice the early exits for that, which i really like
    because each item is handled in a defined space without the danger that it changes later on by mistake
