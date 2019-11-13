import { expect } from 'chai'
import { Item, GildedRose, ITEM_AGED_BRIE, ITEM_SULFURAS, ITEM_BACKSTAGE_PASSES } from '../app/gilded-rose'

describe('Gilded Rose', () => {

    it('should not change the order of the items', () => {
        const gildedRose = new GildedRose([
            new Item('foo', 5, 5),
            new Item('bar', 0, 5),
            new Item('cok', 0, 5)
        ])
        const items = gildedRose.updateQuality()
        expect(items[0].name).to.equal('foo')
        expect(items[1].name).to.equal('bar')
        expect(items[2].name).to.equal('cok')
    })

    // REQ: 3
    it('should decrease the sellIn value for normal items', () => {
        const gildedRose = new GildedRose([
            new Item('foo', 5, 5),
            new Item('bar', 0, 5)
        ])

        let items = gildedRose.updateQuality()
        expect(items[0].sellIn).to.equal(4)
        expect(items[1].sellIn).to.equal(-1)

        items = gildedRose.updateQuality()
        expect(items[0].sellIn).to.equal(3)
        expect(items[1].sellIn).to.equal(-2)
    })

    // REQ: 3
    it('should decrease the quality value for a normal item', () => {
        const gildedRose = new GildedRose([
            new Item('foo', 5, 5),
        ])

        let items = gildedRose.updateQuality()
        expect(items[0].quality).to.equal(4)

        items = gildedRose.updateQuality()
        expect(items[0].quality).to.equal(3)
    })

    // REQ: 4
    it('should degrade quality twice as fast once the sell by date has passed', () => {
        const gildedRose = new GildedRose([
            new Item('foo', 0, 5),
        ])

        let items = gildedRose.updateQuality()
        expect(items[0].quality).to.equal(3)

        items = gildedRose.updateQuality()
        expect(items[0].quality).to.equal(1)
    })


    // REQ: 5
    it('should not decrease the quality value for a normal item with quality 0', () => {
        const gildedRose = new GildedRose([
            new Item('foo', 5, 0),
        ])

        const items = gildedRose.updateQuality()
        expect(items[0].quality).to.equal(0)
    })

    // REQ: 6
    it (`should increase the quality of item 'Aged Brie'`, () => {
        const gildedRose = new GildedRose([
            new Item(ITEM_AGED_BRIE, 5, 5),
        ])

        let items = gildedRose.updateQuality()
        expect(items[0].quality).to.equal(6)
        expect(items[0].sellIn).to.equal(4)

        items = gildedRose.updateQuality()
        expect(items[0].quality).to.equal(7)
    })

    // REQ: 7
    it (`should not increase the quality over 50`, () => {
        const gildedRose = new GildedRose([
            new Item(ITEM_AGED_BRIE, 5, 49),
            new Item(ITEM_BACKSTAGE_PASSES, 5, 49),
        ])

        let items = gildedRose.updateQuality()
        expect(items[0].quality).to.equal(50)
        expect(items[1].quality).to.equal(50)

        items = gildedRose.updateQuality()
        expect(items[0].quality).to.equal(50)
        expect(items[1].quality).to.equal(50)
    })

    // REQ: 8
    it (`should not change the legendary item 'Sulfuras'`, () => {
        const gildedRose = new GildedRose([
            new Item(ITEM_SULFURAS, 7, 7),
        ])

        let items = gildedRose.updateQuality()
        expect(items[0].quality).to.equal(7)
        expect(items[0].sellIn).to.equal(7)
    })

    // REQ: 9.1
    it (`should increase the quality of item 'Backstage passes' by 2 when there are 10 days or less`, () => {
        const gildedRose = new GildedRose([
            new Item(ITEM_BACKSTAGE_PASSES, 12, 10),
            new Item(ITEM_BACKSTAGE_PASSES, 11, 10),
            new Item(ITEM_BACKSTAGE_PASSES, 10, 10),
        ])

        let items = gildedRose.updateQuality()
        expect(items[0].quality).to.equal(11)
        expect(items[1].quality).to.equal(12)
        expect(items[2].quality).to.equal(12)

        items = gildedRose.updateQuality()
        expect(items[0].quality).to.equal(13)
        expect(items[1].quality).to.equal(14)
        expect(items[2].quality).to.equal(14)
    })

    // REQ: 9.2
    it (`should increase the quality of item 'Backstage passes' by 3 when there are 5 days or less`, () => {
        const gildedRose = new GildedRose([
            new Item(ITEM_BACKSTAGE_PASSES, 5, 5),
            new Item(ITEM_BACKSTAGE_PASSES, 4, 5),
        ])

        const items = gildedRose.updateQuality()
        expect(items[0].quality).to.equal(8)
        expect(items[1].quality).to.equal(8)
    })

    // REQ: 9.3
    it (`should drop the quality of item 'Backstage passes' to 0 after the concert (sellDate)`, () => {
        const gildedRose = new GildedRose([
            new Item(ITEM_BACKSTAGE_PASSES, 1, 5),
        ])

        // On the day of the concert (sellIn = 0)
        let items = gildedRose.updateQuality()
        expect(items[0].quality).to.equal(8)

        // Concert is over here
        items = gildedRose.updateQuality()
        expect(items[0].quality).to.equal(0)
    })

    it ('should correctly define normal items', () => {
        const gildedRose = new GildedRose()

        // All the special items should return false
        expect(gildedRose.isNormalItem(new Item(ITEM_AGED_BRIE, 0, 0))).to.equal(false)
        expect(gildedRose.isNormalItem(new Item(ITEM_BACKSTAGE_PASSES, 0, 0))).to.equal(false)
        expect(gildedRose.isNormalItem(new Item(ITEM_SULFURAS, 0, 0))).to.equal(false)

        expect(gildedRose.isNormalItem(new Item('Normal one', 0, 0))).to.equal(true)

    })
})
