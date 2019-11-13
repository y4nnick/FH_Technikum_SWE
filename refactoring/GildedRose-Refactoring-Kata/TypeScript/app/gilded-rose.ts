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

                // Quality should never be negative
                if (item.quality > 0) {

                    // Quality should decrease twice as fast if the sell by date has passed
                    item.quality -= (item.sellIn < 0) ? 2 : 1
                }

                return
            }

            // Handle special item: Aged Brie
            if (item.name === ITEM_AGED_BRIE) {

                if (item.quality < 50) {
                    item.quality += 1
                }

                return
            }

            // Handle special item: Backstage passes
            if (item.name === ITEM_BACKSTAGE_PASSES) {

                // After the concert the backstage pass has no value any more
                if (item.sellIn < 0) {
                    item.quality = 0
                    return
                }

                // Calculate the new quality
                let newQuality = item.quality + 1
                if (item.sellIn <= 5) newQuality += 1
                if (item.sellIn <= 10) newQuality += 1

                // The max quality is 50
                item.quality = Math.min(50, newQuality)

                return
            }
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
