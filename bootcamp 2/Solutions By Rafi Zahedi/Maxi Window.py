# if you fight for it, you deserve it.
# if you deserve it, fight for it.
from collections import deque


def solve(nums, k):
    dq = deque()
    res = []

    for i in range(k):
        while dq and nums[i] >= nums[dq[-1]]:
            dq.pop()
        dq.append(i)

    res.append(nums[dq[0]])

    for i in range(k, len(nums)):
        if dq and dq[0] == i - k:
            dq.popleft()
        while dq and nums[i] >= nums[dq[-1]]:
            dq.pop()

        dq.append(i)
        res.append(nums[dq[0]])

    return res


n, k = map(int, input().split())
lst = list(map(int, input().split()))
ans = solve(lst, k)
for num in ans:
    print(num, end=' ')
