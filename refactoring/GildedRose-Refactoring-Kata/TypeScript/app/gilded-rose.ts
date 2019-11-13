export class Item {
    name: string;
    sellIn: number;
    quality: number;

    constructor(name, sellIn, quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }
}

export const ITEM_AGED_BRIE = 'Aged Brie'
export const ITEM_SULFURAS = 'Sulfuras, Hand of Ragnaros'
export const ITEM_BACKSTAGE_PASSES = 'Backstage passes to a TAFKAL80ETC concert'

export class GildedRose {
    items: Array<Item>;

    constructor(items = [] as Array<Item>) {
        this.items = items;
    }

    updateQuality() {
        
        this.items.forEach((item : Item) => {

            // Special item 'Sulfuras' should always be the same
            if (item.name === ITEM_SULFURAS) {
                return
            }

            // Decrease the sellIn for all the items
            item.sellIn -= 1;


            // Handle a normal item
            if (this.isNormalItem(item)) {
                item.quality -= 1

                // Quality should decrease twice as fast if the sell by date has passed
                if(item.sellIn < 0){
                    item.quality -= 1
                }
            }


            // Handle special item: Aged Brie
            if (item.name === ITEM_AGED_BRIE) {
                item.quality += 1
            }


            // Handle special item: Backstage passes
            if (item.name === ITEM_BACKSTAGE_PASSES) {

                // After the concert the backstage pass has no value any more
                if (item.sellIn < 0) {
                    item.quality = 0
                } else {

                    // Calculate the new quality based on the sell date
                    item.quality += 1
                    if (item.sellIn <= 5) item.quality += 1
                    if (item.sellIn <= 10) item.quality += 1
                }
            }


            // The quality can only be in the interval [0...50]
            if (item.quality < 0) item.quality = 0
            if (item.quality > 50) item.quality = 50
        })

        return this.items;
    }

    /**
     * Determines if the given value is a normal item
     * e.g. the item has no special features included
     */
    public isNormalItem(item: Item): boolean {
        return ![ITEM_SULFURAS, ITEM_BACKSTAGE_PASSES, ITEM_AGED_BRIE]
            .some(specialItemName => item.name === specialItemName)
    }
}
