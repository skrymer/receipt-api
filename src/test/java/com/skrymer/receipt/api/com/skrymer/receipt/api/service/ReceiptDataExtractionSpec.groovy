package com.skrymer.receipt.api.com.skrymer.receipt.api.service

import com.skrymer.receipt.api.model.ReceiptFactory
import com.skrymer.receipt.api.model.ReflectionReceiptFactory
import com.skrymer.receipt.api.reader.OpenCVImageSanitiser
import com.skrymer.receipt.api.model.Receipt
import com.skrymer.receipt.api.reader.TesseractReceiptContentReader
import com.skrymer.receipt.api.model.ReceiptFormat
import com.skrymer.receipt.api.service.ReceiptService
import com.skrymer.receipt.api.service.ReceiptServiceImpl
import info.debatty.java.stringsimilarity.JaroWinkler
import org.hamcrest.CoreMatchers
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

import static org.hamcrest.number.BigDecimalCloseTo.closeTo
import static spock.util.matcher.HamcrestSupport.*

/**
 * Integration spec for extracting receipt data
 */
class ReceiptDataExtractionSpec extends Specification {
    ReceiptService service

    def setup(){
        ReceiptFactory receiptFactory = new ReflectionReceiptFactory();
        receiptFactory.init()

        service = new ReceiptServiceImpl(
                new TesseractReceiptContentReader(new OpenCVImageSanitiser()),
                receiptFactory
        )
    }

    def 'extract receipt information from file'(){
        Path receiptFile
        Receipt receipt

        given : 'a receipt file with 4 items'
        receiptFile = Paths.get('src/test/resources/images/receipt.png')

        when : 'getting receipt information'
        receipt = service.extract(receiptFile, ReceiptFormat.PAC_AND_SAVE)

        then :  'receipt should contain 4 items'
        receipt.getItems().size() == 4

        and: 'items should approximately have same values as given in file'
        isSimilar(receipt.getItems().get(0).getText(), 'PAMS FINEST P .BUTTER SMOOTH 380G', 0.5)
        that receipt.getItems().get(0).getPrice(), CoreMatchers.is(closeTo(new BigDecimal("4.99"), 0.1))
        isSimilar(receipt.getItems().get(1).getText(), 'R/S PEACH/PINEAPPLE FRUIT TEA 20S', 0.5)
        that receipt.getItems().get(1).getPrice(), CoreMatchers.is(closeTo(new BigDecimal("2.99"), 0.1))
        isSimilar(receipt.getItems().get(2).getText(), 'RAPUNZEL VEG BROTH ORGANC 125G', 0.5)
        that receipt.getItems().get(2).getPrice(), CoreMatchers.is(closeTo(new BigDecimal("6.59"), 0.1))
        isSimilar(receipt.getItems().get(3).getText(), 'SILK ULTIMATE BABY WIPES 72PK', 0.5)
        that receipt.getItems().get(3).getPrice(), CoreMatchers.is(closeTo(new BigDecimal("3.99"), 0.1))

        and: 'total should be similar to what is in file'
        that receipt.getTotal(), CoreMatchers.is(closeTo(new BigDecimal('18.56'), 0.5))
    }


    def 'receipt file does not exist'(){
        Path receiptFile

        given : 'a receipt file that does not exist'
        receiptFile = Paths.get("imaginary/file.pdf")

        when: 'getting the receipt information'
        service.extract(receiptFile, ReceiptFormat.PAC_AND_SAVE)

        then: 'a IllegalArgumentException is thrown'
        IllegalArgumentException ex = thrown()
    }

    def isSimilar(String s1, String s2, double delta){
        return new JaroWinkler().similarity(s1,s2) > delta ? true : false
    }
}
