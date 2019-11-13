### Requirements
1. All items have a SellIn value which denotes the number of days we have to sell the item.
2. All items have a Quality value which denotes how valuable the item is.
3. At the end of each day our system lowers both values for every item.
4. Once the sell by date has passed, Quality degrades twice as fast.
5. The Quality of an item is never negative.
6. ”Aged Brie” actually increases in Quality the older it gets.
7. The Quality of an item is never more than 50.
8. ”Sulfuras”, being a legendary item, never has to be sold or decreases in Quality.
9. ”Backstage passes”, like aged brie, increases in Quality as its SellIn value approaches:
    1. Quality increases by 2 when there are 10 days or less
    2. Quality increases by 3 when there are 5 days or less
    3. Quality drops to 0 after the concert.
