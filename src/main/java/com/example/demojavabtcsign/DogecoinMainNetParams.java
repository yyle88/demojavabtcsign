package com.example.demojavabtcsign;

import com.google.common.base.Preconditions;
import org.bitcoinj.core.Block;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.bitcoinj.params.AbstractBitcoinNetParams;

// 这个是从github里拷来的，你需要去找原作者的，搜 "DogecoinMainNetParams"，当然拷贝这里的也行
public class DogecoinMainNetParams extends AbstractBitcoinNetParams {
    public static final int MAINNET_MAJORITY_WINDOW = 2000;
    public static final int MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED = 1900;
    public static final int MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE = 1500;
    private static final long GENESIS_TIME = 1386325540L;
    private static final long GENESIS_NONCE = 99943L;
    private static final Sha256Hash GENESIS_HASH = Sha256Hash.wrap("1a91e3dace36e2be3bf030a65679fe821aa1d6ef92e7c9902eb318182c355691");
    private static DogecoinMainNetParams instance;

    public DogecoinMainNetParams() {
        this.id = "org.dogecoin.production";
        this.targetTimespan = 1209600;
        this.maxTarget = Utils.decodeCompactBits(0x1e0ffff0L);
        this.port = 22556;
        this.packetMagic = 0xc0c0c0c0L;
        this.dumpedPrivateKeyHeader = 158;
        this.addressHeader = 30;
        this.p2shHeader = 22;
        this.segwitAddressHrp = "doge";
        this.spendableCoinbaseDepth = 100;
        this.bip32HeaderP2PKHpub = 0x02facafd;
        this.bip32HeaderP2PKHpriv = 0x02fac398;
        this.majorityEnforceBlockUpgrade = 750;
        this.majorityRejectBlockOutdated = 950;
        this.majorityWindow = MAINNET_MAJORITY_WINDOW;
        this.checkpoints.put(    0, Sha256Hash.wrap("1a91e3dace36e2be3bf030a65679fe821aa1d6ef92e7c9902eb318182c355691"));
        this.checkpoints.put( 42279, Sha256Hash.wrap("8444c3ef39a46222e87584ef956ad2c9ef401578bd8b51e8e4b9a86ec3134d3a"));
        this.checkpoints.put( 42400, Sha256Hash.wrap("557bb7c17ed9e6d4a6f9361cfddf7c1fc0bdc394af7019167442b41f507252b4"));
        this.checkpoints.put(104679, Sha256Hash.wrap("35eb87ae90d44b98898fec8c39577b76cb1eb08e1261cfc10706c8ce9a1d01cf"));
        this.checkpoints.put(128370, Sha256Hash.wrap("3f9265c94cab7dc3bd6a2ad2fb26c8845cb41cff437e0a75ae006997b4974be6"));
        this.checkpoints.put(145000, Sha256Hash.wrap("cc47cae70d7c5c92828d3214a266331dde59087d4a39071fa76ddfff9b7bde72"));
        this.checkpoints.put(165393, Sha256Hash.wrap("7154efb4009e18c1c6a6a79fc6015f48502bcd0a1edd9c20e44cd7cbbe2eeef1"));
        this.checkpoints.put(186774, Sha256Hash.wrap("3c712c49b34a5f34d4b963750d6ba02b73e8a938d2ee415dcda141d89f5cb23a"));
        this.checkpoints.put(199992, Sha256Hash.wrap("3408ff829b7104eebaf61fd2ba2203ef2a43af38b95b353e992ef48f00ebb190"));
        this.checkpoints.put(225000, Sha256Hash.wrap("be148d9c5eab4a33392a6367198796784479720d06bfdd07bd547fe934eea15a"));
        this.checkpoints.put(250000, Sha256Hash.wrap("0e4bcfe8d970979f7e30e2809ab51908d435677998cf759169407824d4f36460"));
        this.checkpoints.put(270639, Sha256Hash.wrap("c587a36dd4f60725b9dd01d99694799bef111fc584d659f6756ab06d2a90d911"));
        this.checkpoints.put(299742, Sha256Hash.wrap("1cc89c0c8a58046bf0222fe131c099852bd9af25a80e07922918ef5fb39d6742"));
        this.checkpoints.put(323141, Sha256Hash.wrap("60c9f919f9b271add6ef5671e9538bad296d79f7fdc6487ba702bf2ba131d31d"));
        this.checkpoints.put(339202, Sha256Hash.wrap("8c29048df5ae9df38a67ea9470fdd404d281a3a5c6f33080cd5bf14aa496ab03"));
        this.checkpoints.put(350000, Sha256Hash.wrap("2bdcba23a47049e69c4fec4c425462e30f3d21d25223bde0ed36be4ea59a7075"));
        this.checkpoints.put(370005, Sha256Hash.wrap("7be5af2c5bdcb79047dcd691ef613b82d4f1c20835677daed936de37a4782e15"));
        this.checkpoints.put(371337, Sha256Hash.wrap("60323982f9c5ff1b5a954eac9dc1269352835f47c2c5222691d80f0d50dcf053"));
        this.checkpoints.put(400002, Sha256Hash.wrap("a5021d69a83f39aef10f3f24f932068d6ff322c654d20562def3fac5703ce3aa"));
        this.dnsSeeds = new String[] {
                "seed.multidoge.org",
                "seed2.multidoge.org",
                "seed.doger.dogecoin.com"
        };
        this.dnsSeeds = new String[]{"seed.bitcoin.sipa.be", "dnsseed.bluematt.me", "dnsseed.bitcoin.dashjr.org", "seed.bitcoinstats.com", "seed.bitcoin.jonasschnelli.ch", "seed.btc.petertodd.org", "seed.bitcoin.sprovoost.nl", "dnsseed.emzy.de", "seed.bitcoin.wiz.biz"};
    }

    public static synchronized DogecoinMainNetParams get() {
        if (instance == null) {
            instance = new DogecoinMainNetParams();
        }

        return instance;
    }

    public Block getGenesisBlock() {
        synchronized(GENESIS_HASH) {
            if (this.genesisBlock == null) {
                this.genesisBlock = Block.createGenesis(this);
                this.genesisBlock.setDifficultyTarget(0x1e0ffff0L);
                this.genesisBlock.setTime(1386325540L);
                this.genesisBlock.setNonce(99943L);
                Preconditions.checkState(this.genesisBlock.getHash().equals(GENESIS_HASH), "Invalid genesis hash");
            }
        }

        return this.genesisBlock;
    }

    public String getPaymentProtocolId() {
        return "org.dogecoin.production";
    }
}
