local goodsStoreKey = KEYS[1];
local balanceKey = KEYS[2];
local money = ARGV[1];

local storeNum = redis.call("get", goodsStoreKey);
if tonumber(storeNum)<=0 then
return 0;
end

local balance = redis.call("get", balanceKey);
if tonumber(balance)< tonumber(money) then
return 0;
else

redis.call("decr", goodsStoreKey)
redis.call("decrby", balanceKey, money)
 end
return 1;



