def subset_sum_exists(arr, target):
    n = len(arr)

    dp = [[False] * (target + 1) for _ in range(n + 1)]

    for i in range(n + 1):
        dp[i][0] = True

    for i in range(1, n + 1):
        for j in range(1, target + 1):
            if arr[i - 1] <= j:
                dp[i][j] = dp[i - 1][j] or dp[i - 1][j - arr[i - 1]]
            else:
                dp[i][j] = dp[i - 1][j]

    return dp[n][target]


for _ in range(int(input())):
    n = int(input())
    arr = list(map(int, input().split()))
    target = int(input())

    if subset_sum_exists(arr, target):
        print("Yes")
    else:
        print("No")
