package com.example.demojavabtcsign;

import com.google.common.base.Preconditions;
import org.bitcoinj.core.Block;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.bitcoinj.params.AbstractBitcoinNetParams;

// 这个是从github里拷来的，你需要去找原作者的，搜 "DogecoinTestNet3Params"，当然拷贝这里的也行
public class DogecoinTestNet3Params extends AbstractBitcoinNetParams {
    public static final int TESTNET_MAJORITY_WINDOW = 1000;
    public static final int TESTNET_MAJORITY_REJECT_BLOCK_OUTDATED = 750;
    public static final int TESTNET_MAJORITY_ENFORCE_BLOCK_UPGRADE = 501;
    private static final long GENESIS_TIME = 1386325540L;
    private static final long GENESIS_NONCE = 99943L;
    private static final Sha256Hash GENESIS_HASH = Sha256Hash.wrap("bb0a78264637406b6360aad926284d544d7049f45189db5664f3c4d07350559e");
    private static DogecoinTestNet3Params instance;

    public DogecoinTestNet3Params() {
        this.id = "org.dogecoin.testnet";
        this.targetTimespan = 1209600;
        this.maxTarget = Utils.decodeCompactBits(0x1e0ffff0L);
        this.port = 44556;
        this.packetMagic = 0xfcc1b7dcL;
        this.dumpedPrivateKeyHeader = 158;
        this.addressHeader = 113;
        this.p2shHeader = 196;
        this.segwitAddressHrp = "tdge";
        this.spendableCoinbaseDepth = 30;
        this.bip32HeaderP2PKHpub = 0x043587CF;
        this.bip32HeaderP2PKHpriv = 0x04358394;
        this.majorityEnforceBlockUpgrade = TESTNET_MAJORITY_ENFORCE_BLOCK_UPGRADE;
        this.majorityRejectBlockOutdated = TESTNET_MAJORITY_REJECT_BLOCK_OUTDATED;
        this.majorityWindow = TESTNET_MAJORITY_WINDOW;

        dnsSeeds = new String[]{"testseed.jrn.me.uk"};
    }

    public static synchronized DogecoinTestNet3Params get() {
        if (instance == null) {
            instance = new DogecoinTestNet3Params();
        }

        return instance;
    }

    public Block getGenesisBlock() {
        synchronized (GENESIS_HASH) {
            if (this.genesisBlock == null) {
                this.genesisBlock = Block.createGenesis(this);
                this.genesisBlock.setDifficultyTarget(0x1e0ffff0L);
                this.genesisBlock.setTime(1391503289L);
                this.genesisBlock.setNonce(997879);
                Preconditions.checkState(this.genesisBlock.getHash().equals(GENESIS_HASH), "Invalid genesis hash");
            }
        }

        return this.genesisBlock;
    }

    public String getPaymentProtocolId() {
        return "org.dogecoin.testnet";
    }
}
