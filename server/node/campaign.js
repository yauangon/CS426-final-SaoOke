const { Blockchain, Transaction } = require('./blockchain');

class Campaign {
    constructor(campaignName, ownerKey, ownerName, targetAmount, expireDate, description, propaganda = "") {
        this.campaignName = campaignName
        this.ownerKey = ownerKey
        this.ownerName = ownerName
        this.targetAmount = targetAmount
        this.expireDate = expireDate
        this.description = description
        this.propaganda = propaganda
        this.transactions = []
        this.requestHelpList = []
    }

    getReceivedAmount() {
        amount = 0;
        this.transactions.forEach(transaction => {
            if (this.ownerKey == transaction.transaction.toAddress) {
                amount += transaction.transaction.amount;
            } else {
                amount -= transaction.transaction.amount;
            }
        });
        return amount;
    }

    getHistory() {
        var logs = []
        this.transactions.forEach(transaction => {
            if (transaction.transaction.toAddress == this.ownerKey) {
                logs.push({
                    "amount" : transaction.transaction.amount,
                    "message" : transaction.message
                })
            } else {
                logs.push({
                    "amount" : -transaction.transaction.amount,
                    "message" : transaction.message
                })
            }
        });
    }

    getAllDonators() {
        var donators = []
        this.transactions.forEach(transaction => {
            if (transaction.transaction.fromAddress != this.ownerKey) {
                donators.push(transaction.transaction.fromAddress)
            }
        });
        return donators
    }

    requestHelp(username, amount, message) {
        this.requestHelpList.push({
            "username" : username,
            "amount" : amount,
            "message" : message
        })
    }

    getRequestHelpList() {
        return this.requestHelpList;
    }
}

class CampaignFactory {
    constructor() {
        this.campaignList = []
    }

    getCampaignByCampaignName(campaignName) {
        var campaign = null
        this.campaignList.forEach(element => {
            if (element.campaignName == campaignName) {
                campaign = element
            }
        });
        return campaign
    }

    createCampaign(campaignName, ownerKey, ownerName, targetAmount, expireDate, description, propaganda = "") {
        this.campaignList.forEach(element => {
            if (element.campaignName == campaignName) {
                throw new Error("This campain name is exists")
            }
        });
        this.campaignList.push(new Campaign(campaignName, ownerKey, ownerName, targetAmount, 
                expireDate, description, propaganda))
    }

    donate(donatorKey, campaignName, transaction, message) {
        var targetCampain = null
        this.campaignList.forEach(campaign => {
            if (campaign.campaignName == campaignName) {
                targetCampain = campaign
                break
            }
        });
        if (targetCampain == null) {
            throw new Error("Campaign not found");
        }

        // make new transaction here
        targetCampain.push({
            "transaction" : transaction,
            "message" : message
        })
        return true;
        //
    }

    give(campaignName, ownerKey, receiverKey, transaction, message) {
        var campaign = null
        this.campaignList.forEach(element => {
            if (element.campaignName == campaignName) {
                campaign = element
                break
            }
        });

        if (campaign == null) {
            throw new Error("campain not found!")
        }

        if (campaign.ownerKey != ownerKey) {
            throw new Error("campaign not match")
        }
    
        //make new transaction here
        campaign.push({
            "transaction" : transaction,
            "message" : message
        })
        return true
        //
    }

    getCampaignInformation(campaignName) {
        this.campaignList.forEach(element => {
            if (element.campaignName == campaignName) {
                return {
                    "campaignName" : element.campaignName,
                    "ownerKey" : element.ownerKey,
                    "ownerName" : element.ownerName,
                    "description" : element.description,
                    "targetAmount" : element.targetAmount,
                    "expireDate" : element.expireDate,
                    "propaganda" : element.propaganda,
                    "total_amount" : element.getReceivedAmount()
                }
            }
        });
        return null;
    }

    getCampaignKey(campaignName) {
        this.campaignList.forEach(element => {
            if (element.campaignName == campaignName) {
                return element.ownerKey;
            }
        });
        return null;
    }

    getCampaignHistory(campaignName) {
        this.campaignList.forEach(campaign => {
            if (Campaign.campaignName == campaignName) {
                return campaign.getHistory()
            }
        });
    }

    getAllCampaign() {
        var result = []
        this.campaignList.forEach(campaign => {
            result.push({
                "campaignName" : campaign.campaignName,
                "campaignOwnerKey" : campaign.campaignOwnerKey,
                "expireDate" : campaign.expireDate,
                "description" : campaign.description,
                "totalAmount" : campaign.getReceivedAmount()
            })
        });
    }

    getAllDonators(campaignName) {
        var result = []
        var campaign = this.getCampaignByCampaignName(campaignName)
        if (campaign != null) {
            return campaign.getAllDonators
        }
        return null
    }

    requestHelp(campaignName, username, amount, message) {
        var campaign = this.getCampaignByCampaignName(campaignName)
        if (campaign == null) {
            throw new Error("campaign does not exists")
        }
        campaign.requestHelp(username, amount, message)
        return true
    }

    getRequestHelpList(campaignName) {
        var campaign = this.getCampaignByCampaignName(campaignName)
        if (campaign == null) {
            throw new Error("Campaign does not exists")
        }
        return campaign.getRequestHelpList()
    }
}

module.exports.CampaignFactory = CampaignFactory